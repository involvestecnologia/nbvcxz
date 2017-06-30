package br.com.involves.password.estimator.matching;

import org.junit.Test;

import br.com.involves.password.estimator.matching.PasswordMatcher;
import br.com.involves.password.estimator.matching.SequenceMatcher;
import br.com.involves.password.estimator.matching.match.Match;
import br.com.involves.password.estimator.resources.Configuration;
import br.com.involves.password.estimator.resources.ConfigurationBuilder;

import java.util.List;

/**
 * @author Adam Brusselback
 */
public class SequenceMatcherTest
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
        PasswordMatcher matcher = new SequenceMatcher();

        password = "abcdefy";
        matches = matcher.match(configuration, password);

        assert matches.get(0).getToken().equals("abcdef");


        password = "aW3ennbfT6y";
        matches = matcher.match(configuration, password);

        assert matches.size() == 0;
    }

}
