package br.com.involves.password.estimator.matching;

import org.junit.Test;

import br.com.involves.password.estimator.matching.PasswordMatcher;
import br.com.involves.password.estimator.matching.SpacialMatcher;
import br.com.involves.password.estimator.matching.match.Match;
import br.com.involves.password.estimator.matching.match.SpacialMatch;
import br.com.involves.password.estimator.resources.Configuration;
import br.com.involves.password.estimator.resources.ConfigurationBuilder;

import java.util.List;

/**
 * @author Adam Brusselback
 */
public class SpacialMatcherTest
{

    final Configuration configuration = new ConfigurationBuilder().createConfiguration();

    /**
     * Test of match method, of class DateMatcher.
     */
    @Test
    public void testMatch()
    {

        List<Match> matches;
        String password;
        PasswordMatcher matcher = new SpacialMatcher();

        password = "aw3ennbft6y";
        matches = matcher.match(configuration, password);

        assert matches.get(0).getToken().equals("aw3e");
        assert matches.get(1).getToken().equals("ft6y");
        assert SpacialMatch.class.cast(matches.get(0)).getShiftedNumber() == 0;
        assert SpacialMatch.class.cast(matches.get(1)).getShiftedNumber() == 0;
        assert SpacialMatch.class.cast(matches.get(0)).getTurns() == 2;
        assert SpacialMatch.class.cast(matches.get(1)).getTurns() == 2;


        password = "aW3ennbfT6y";
        matches = matcher.match(configuration, password);

        assert matches.get(0).getToken().equals("aW3e");
        assert matches.get(1).getToken().equals("fT6y");
        assert SpacialMatch.class.cast(matches.get(0)).getShiftedNumber() == 1;
        assert SpacialMatch.class.cast(matches.get(1)).getShiftedNumber() == 1;
        assert SpacialMatch.class.cast(matches.get(0)).getTurns() == 2;
        assert SpacialMatch.class.cast(matches.get(1)).getTurns() == 2;


        password = "h";
        matches = matcher.match(configuration, password);

        assert matches.isEmpty();


        password = "hl5ca";
        matches = matcher.match(configuration, password);

        assert matches.isEmpty();
    }

}
