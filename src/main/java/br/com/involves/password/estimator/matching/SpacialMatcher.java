package br.com.involves.password.estimator.matching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.involves.password.estimator.matching.match.Match;
import br.com.involves.password.estimator.matching.match.SpacialMatch;
import br.com.involves.password.estimator.resources.AdjacencyGraph;
import br.com.involves.password.estimator.resources.AdjacencyGraphUtil;
import br.com.involves.password.estimator.resources.Configuration;

/**
 * Look for every part of the password that is spacial pattern
 * <br>
 * This will only return matches where there are at least two consecutive characters.
 *
 * @author Adam Brusselback
 */
public final class SpacialMatcher implements PasswordMatcher
{
    public List<Match> match(final Configuration configuration, final String password)
    {
        List<Match> matches = new ArrayList<>();
        Map<Integer, Set<Character>> neighbors = new HashMap<>();

        for (AdjacencyGraph adjacencyGraph : configuration.getAdjacencyGraphs())
        {
            // Get all the neighbors for each character
            for (int i = 0; i < password.length(); i++)
            {
                neighbors.put(i, AdjacencyGraphUtil.getNeighbors(adjacencyGraph, password.charAt(i)));
            }

            // Build out matches
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < password.length(); i++)
            {
                Character character = password.charAt(i);
                int neighbors_i = i + 1;
                if (neighbors_i < neighbors.size())
                {
                    boolean added = false;
                    for (Character neighbor : neighbors.get(neighbors_i))
                    {
                        if (neighbor.equals(character))
                        {
                            builder.append(character);
                            added = true;
                            break;
                        }
                    }
                    if (!added)
                    {
                        builder.append(character);
                        if (builder.length() > 2)
                        {
                            matches.add(new SpacialMatch(builder.toString(), configuration, i - builder.length() + 1, i, adjacencyGraph, AdjacencyGraphUtil.getTurns(adjacencyGraph, builder.toString()), AdjacencyGraphUtil.getShifts(adjacencyGraph, builder.toString())));
                        }
                        builder.setLength(0);
                    }

                }
                else
                {
                    builder.append(character);
                    if (builder.length() > 2)
                    {
                        matches.add(new SpacialMatch(builder.toString(), configuration, i - builder.length() + 1, i, adjacencyGraph, AdjacencyGraphUtil.getTurns(adjacencyGraph, builder.toString()), AdjacencyGraphUtil.getShifts(adjacencyGraph, builder.toString())));
                    }
                    builder.setLength(0);
                }
            }
        }
        return matches;
    }
}
