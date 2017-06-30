package br.com.involves.password.estimator.matching;

import org.junit.Test;

import br.com.involves.password.estimator.matching.PasswordMatcher;
import br.com.involves.password.estimator.matching.RepeatMatcher;
import br.com.involves.password.estimator.matching.match.Match;
import br.com.involves.password.estimator.matching.match.RepeatMatch;
import br.com.involves.password.estimator.resources.Configuration;
import br.com.involves.password.estimator.resources.ConfigurationBuilder;

import java.util.List;

/**
 * @author Adam Brusselback
 */
public class RepeatMatcherTest
{
    final Configuration configuration = new ConfigurationBuilder().createConfiguration();

    /**
     * Test of match method, of class RepeatMatcher.
     */
    @Test
    public void testMatcher()
    {

        List<Match> matches;
        String password;
        PasswordMatcher matcher = new RepeatMatcher();

        password = "igfigf";
        matches = matcher.match(configuration, password);

        assert RepeatMatch.class.cast(matches.get(0)).getRepeatingCharacters().equals("igf");
        assert RepeatMatch.class.cast(matches.get(0)).getRepeat() == 2;
        assert matches.get(0).getToken().equals("igfigf");


        password = "igfigfigf";
        matches = matcher.match(configuration, password);

        assert RepeatMatch.class.cast(matches.get(0)).getRepeatingCharacters().equals("igf");
        assert RepeatMatch.class.cast(matches.get(0)).getRepeat() == 3;
        assert matches.get(0).getToken().equals("igfigfigf");


        password = "6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p";
        matches = matcher.match(configuration, password);

        assert RepeatMatch.class.cast(matches.get(0)).getRepeatingCharacters().equals("6p");
        assert RepeatMatch.class.cast(matches.get(0)).getRepeat() == 18;
        assert matches.get(0).getToken().equals("6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p");

        password = "8gmsz";
        matches = matcher.match(configuration, password);
        assert matches.size() == 0;
    }

}
