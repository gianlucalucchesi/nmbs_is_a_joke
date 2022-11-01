package com.nmbs_is_a_joke.api.helper;

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
 *
 */
public class TwitterHelper {

    public void postTweet(String tweet) throws TwitterException {
        TwitterFactory tf = new TwitterFactory();
        Twitter twitter = tf.getInstance();
        twitter.updateStatus(tweet);
    }

}
