package com.nmbs_is_a_joke.api.helper;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * twitter4j.properties file needed in resources folder with following properties:
 * oauth.consumerKey=
 * oauth.consumerSecret=
 * oauth.accessToken=
 * oauth.accessTokenSecret=
 * ==> can be retrieved from Twitter Developer Platform
 * https://www.youtube.com/watch?v=yROTjD7Tl-0
 * https://www.youtube.com/watch?v=Ak37Ri06faY
 */
public class TwitterHelper {

    ch.qos.logback.classic.Logger log =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.nmbs_is_a_joke");

    public TwitterHelper() {
        log.setLevel(Level.INFO);
    }

    public void postTweet(String tweet) throws TwitterException {
        TwitterFactory tf = new TwitterFactory();
        Twitter twitter = tf.getInstance();
        twitter.updateStatus(tweet);
        log.info("Tweet sent: {}", tweet);
    }

}
