package com.revinate.instagram;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InstagramTest {
	@Test
	public void testInstantiationNBaseCase() throws Exception{
		Instagram instagram = new Instagram();
        assertThat(instagram.userList.size(), is(0));
        instagram.postMedia(1,11);
        Thread.sleep(1);
        assertThat(instagram.userList.size(), is(1));
        Instagram.User u =(Instagram.User)instagram.userList.get(1); 
        assertThat(u.getPosts().size(), is(1));
        assertThat(u.getPosts().get(0).getId(), is(11));
        
        instagram.postMedia(2,12);
        Thread.sleep(1);
        instagram.postMedia(1,13);
        Thread.sleep(1);
        assertThat(instagram.userList.size(), is(2));
        Instagram.User u2 =(Instagram.User)instagram.userList.get(2); 
        assertThat(u2.getPosts().size(), is(1));
        assertThat(u2.getPosts().get(0).getId(), is(12));
        assertThat(u.getPosts().get(0).getId(), is(13));
        
        instagram.follow(1, 2);
        assertThat(u.getFollowing().size(), is(1));
        assertThat(u.getFollowing().get(0).getId(), is(2));
        assertThat(instagram.getMediaFeed(1).size(), is(3));
        assertThat(instagram.getMediaFeed(2).size(), is(1));
        instagram.follow(2,1);
        assertThat(instagram.getMediaFeed(2).size(), is(3));
        instagram.unfollow(1,2);
        assertThat(instagram.getMediaFeed(1).size(), is(2));
        instagram.unfollow(1,1);
        assertThat(instagram.getMediaFeed(1).size(), is(2));
        instagram.unfollow(2,1);
        assertThat(instagram.getMediaFeed(2).size(), is(1));
        instagram.unfollow(2,2);
        assertThat(instagram.getMediaFeed(2).size(), is(1));
        instagram.follow(2,5);
        assertThat(instagram.getMediaFeed(2).size(), is(1));
        assertThat(instagram.userList.size(), is(2));
        
        
    }
 
	@Test
	public void testLargeNoOfPosts() throws Exception{

		Instagram instagram = new Instagram();
        assertThat(instagram.userList.size(), is(0));
        instagram.postMedia(1,11);
        Thread.sleep(1);
        instagram.postMedia(2,12);
        Thread.sleep(1);
        
        instagram.postMedia(2,13);
        Thread.sleep(1);
        
        instagram.postMedia(3,14);
        Thread.sleep(1);
        
        instagram.postMedia(3,15);
        Thread.sleep(1);
        
        instagram.postMedia(3,16);
        Thread.sleep(1);
        
        instagram.postMedia(4,17);
        Thread.sleep(1);
        
        instagram.postMedia(4,18);
        Thread.sleep(1);
        
        instagram.postMedia(4,19);
        Thread.sleep(1);
        
        instagram.postMedia(3,20);
        Thread.sleep(1);
        
        instagram.postMedia(2,21);
        Thread.sleep(1);
        
        instagram.postMedia(2,22);
        Thread.sleep(1);
        instagram.follow(1, 2);
        instagram.follow(1, 3);
        instagram.follow(1, 4);
        List<Integer> feed = instagram.getMediaFeed(1);
        assertThat(feed.get(0), is(22));
        assertThat(feed.get(1), is(21));
        assertThat(feed.get(2), is(20));
        assertThat(feed.get(3), is(19));
        assertThat(feed.get(4), is(18));
        assertThat(feed.get(5), is(17));
        assertThat(feed.get(6), is(16));
        assertThat(feed.get(7), is(15));
        assertThat(feed.get(8), is(14));
        assertThat(feed.get(9), is(13));
	}
    @Test
    public void getMediaFeed() throws Exception {
        Instagram instagram = new Instagram();
        instagram.postMedia(1,1025);
        Thread.sleep(1);
        instagram.postMedia(2,1135);
        Thread.sleep(1);
        instagram.postMedia(3,1092);
        Thread.sleep(1);
        instagram.postMedia(3,3094);
        Thread.sleep(1);
        instagram.postMedia(1,1022);

        instagram.follow(1, 2);
        instagram.follow(1, 3);

        List<Integer> feed = instagram.getMediaFeed(1);
        assertThat(feed.get(0), is(1022));
        assertThat(feed.get(1), is(3094));
        assertThat(feed.get(2), is(1092));
        assertThat(feed.get(3), is(1135));
        assertThat(feed.get(4), is(1025));

        instagram.unfollow(1, 3);

        List<Integer> feed_2 = instagram.getMediaFeed(1);
        assertThat(feed_2.get(0), is(1022));
        assertThat(feed_2.get(1), is(1135));
        assertThat(feed_2.get(2), is(1025));
    }
}