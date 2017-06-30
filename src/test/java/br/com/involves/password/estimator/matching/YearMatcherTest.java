package br.com.involves.password.estimator.matching;

import org.junit.Test;

import br.com.involves.password.estimator.matching.PasswordMatcher;
import br.com.involves.password.estimator.matching.YearMatcher;
import br.com.involves.password.estimator.matching.match.Match;
import br.com.involves.password.estimator.resources.Configuration;
import br.com.involves.password.estimator.resources.ConfigurationBuilder;

import java.util.List;

/**
 * @author Adam Brusselback
 */
public class YearMatcherTest
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
        PasswordMatcher matcher = new YearMatcher();

        password = "abcdefy2010";
        matches = matcher.match(configuration, password);

        assert matches.get(0).getToken().equals("2010");


        password = "1950aW3ennbfT6y";
        matches = matcher.match(configuration, password);

        assert matches.get(0).getToken().equals("1950");


        password = "1850aW3ennbfT6y";
        matches = matcher.match(configuration, password);

        assert matches.size() == 0;
    }

}
