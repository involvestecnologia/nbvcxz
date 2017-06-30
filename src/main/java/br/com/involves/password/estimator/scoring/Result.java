package br.com.involves.password.estimator.scoring;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import br.com.involves.password.estimator.matching.match.BruteForceMatch;
import br.com.involves.password.estimator.matching.match.Match;
import br.com.involves.password.estimator.resources.Configuration;
import br.com.involves.password.estimator.resources.Feedback;
import br.com.involves.password.estimator.resources.FeedbackUtil;

/**
 * This class contains all info about the entropy calculation.
 *
 * @author Adam Brusselback
 */
public class Result
{
    final Configuration configuration;
    final String password;
    final List<Match> matches;

    /**
     * @param configuration the {@link Configuration} object.
     * @param password      the password this result was generated for
     * @param matches       list of matches which when combined make up the original password
     * @throws IllegalStateException if the matches do not equal the original password, this will be thrown.
     */
    public Result(final Configuration configuration, final String password, final List<Match> matches) throws IllegalStateException
    {
        this.configuration = configuration;
        this.password = password;
        this.matches = matches;

        if (!this.isValid())
        {
            throw new IllegalStateException("There was an unexpected error and all of the matches put together do not equal the original password.");
        }
    }

    /**
     * Checks if the sum of the matches equals the original password.
     *
     * @return {@code true} if valid; {@code false} if invalid.
     */
    private boolean isValid()
    {
        StringBuilder builder = new StringBuilder();
        for (Match match : matches)
        {
            builder.append(match.getToken());
        }

        return password.equals(builder.toString());
    }

    /**
     * Returns the entropy for this {@code Result}.
     *
     * @return the estimated entropy as a {@code double}.
     */
    public Double getEntropy()
    {
        double entropy = 0;
        for (Match match : matches)
        {
            entropy += match.calculateEntropy();
        }
        return entropy;
    }

    /**
     * The estimated number of tries required to crack this password
     *
     * @return the estimated number of guesses as a {@code BigDecimal}
     */
    public BigDecimal getGuesses()
    {
        final Double guesses_tmp = Math.pow(2, getEntropy());
        return new BigDecimal(guesses_tmp.isInfinite() ? Double.MAX_VALUE : guesses_tmp).setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * The matches that comprise this result.
     *
     * @return a {@code List} of {@code Match} that were used in this result estimation.
     */
    public List<Match> getMatches()
    {
        return this.matches;
    }

    /**
     * The original password passed in.
     *
     * @return {@code String} of the original password.
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Returns whether the minimum entropy specified in the config was met.
     *
     * @return {@code true} if minimum entropy is met; {@code false} if not.
     */
    public boolean isMinimumEntropyMet()
    {
        return this.getEntropy().compareTo(configuration.getMinimumEntropy()) >= 0;
    }

    /**
     * Returns whether the password is considered to be random.
     *
     * @return true if the password is considered random, false otherwise.
     */
    public boolean isRandom()
    {
        boolean is_random = true;
        for (Match match : matches)
        {
            if (!(match instanceof BruteForceMatch))
            {
                is_random = false;
                break;
            }
        }
        return is_random;
    }

    /**
     * Returns the configuration used to generate this result.
     *
     * @return {@code Configuration} that was used to generate this {@code Result}.
     */
    public Configuration getConfiguration()
    {
        return configuration;
    }

    /**
     * Returns feedback to the user to suggest ways to improve their password.
     *
     * @return a {@code Feedback} object with suggestions for the user.
     */
    public Feedback getFeedback()
    {
        return FeedbackUtil.getFeedback(this);
    }
}