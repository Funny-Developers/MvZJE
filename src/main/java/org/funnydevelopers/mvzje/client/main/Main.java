package org.funnydevelopers.mvzje.client.main;

import org.funnydevelopers.mvzje.client.MvZ;

/**
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class Main {
    public static void main(String[] args) {
        try (var mvz = MvZ.getInstance()) {
            mvz.launch();
        }
    }
}
