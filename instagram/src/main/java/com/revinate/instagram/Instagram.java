package com.revinate.instagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your Instagram object will be instantiated and called as such:
 * Instagram instagram = new Instagram();
 * instagram.postMedia(userId,mediaId);
 * List<Integer> feed = instagram.getMediaFeed(userId);
 * instagram.follow(followerId,followedId);
 * instagram.unfollow(followerId,followedId);
 */

public class Instagram {
	HashMap<Integer,User> userList;
	/**A new class for Posts*/
	public class Post implements Comparable<Post>{
		private Integer id;
		private long time;
		//NOTE: Constructor for Post to initialize id and timestamp
		public Post(Integer id){
			this.id = id;
			this.time = System.currentTimeMillis();
		}
		//NOTE: ID GETTER
		public Integer getId(){
			return id;
		}
		//NOTE: TIME GETTER
		public Long getTimeStamp(){
			return time;
		}
		
		//NOTE: Sort Posts based on timestamp
		public int compareTo(Post p){
			return -p.getTimeStamp().compareTo(time);
		}
		
		@Override
		public String toString(){
			return id.toString()+" "+time+"; ";
		}
		
	}
	
	/**A new class for Users*/
	public class User{
		private Integer id;
		private List<Post> posts = null;
		private HashMap<Integer,User> follows = null;
		
		//FUNCTION: Constructor for User
		public User(int id) {
			this.id = id;
		}
		
		//FUNCTION: ID GETTER
		public Integer getId() {
			return id;
		}
		
		//FUNCTION: Fetch posts made by user
		public List<Post> getPosts(){
			return posts;
		}
		
		//FUNCTION: Fetch followed users
		public List<User> getFollowing() {
			return new ArrayList<User>(follows.values());
		}
		
		//FUNCTION: Make Post
		public void post(Integer media){
			//NOTE: Check if first post
			if(null == posts)
				this.posts = new LinkedList<Post>();
			Post p = new Post(media);
			//NOTE: Add to head to retain freshness
			posts.add(0, p);
		}
		
		//FUNCTION: Follow a specified user
		public void follow(User u){
			//NOTE: Check if first follow
			if(null == follows)
				this.follows = new HashMap<Integer,User>();
			follows.put(u.getId(),u);
		}
		
		//FUNCTION: Unfollow a specified user
		public void unFollow(User u){
			if(null != follows)
				follows.remove(u.getId());
		}
		
		//FUNCTION: Get top 10 user feed
		public List<Integer> getFeed(){
			PriorityQueue<Post> feed = new PriorityQueue<Post>();
			//NOTE: Consider users posts
			if(null != posts){
				//NOTE: Fetch top k latest posts
				fetchTopKFeed(feed,posts);
			}
			//NOTE: Consider each followed user
			if(null != follows){
				for(User u: follows.values()){
					if(null != u.getPosts()){
						//NOTE: Fetch top k latest posts
						fetchTopKFeed(feed,u.getPosts());
					}
				}
			}
			//NOTE: Result List
			LinkedList<Integer> result = new LinkedList<Integer>();
			while(!feed.isEmpty()){
				result.add(0,feed.poll().getId());
			}
			return result;
		}
		
		//FUNCTION: Fetch top k latest posts
		public void fetchTopKFeed(PriorityQueue<Post> feed, List<Post> userPosts){
			for( int i = 0;i<userPosts.size(); i++){
				feed.add(userPosts.get(i));
				//NOTE: if size is greater than 10 remove extra
				if(feed.size()>10){
					//NOTE: if newest post of user is older than current oldest timestamp in top k feed ignore users post
					if(feed.peek().getTimeStamp()>userPosts.get(i).getTimeStamp()){
						break;
					}
				}
			}
		}
	}
	
	
    /** Initialize your data structure here. */
    public Instagram() {
    	//NOTE: Map ID to User Object
    	this.userList = new HashMap<Integer,User>();
    }

    /** Add a new media. */
    public void postMedia(int userId, int mediaId) {
       	User u = null;
      //NOTE: Check if user exist
    	if(userList.containsKey(userId))
    		u = userList.get(userId);
    	else{
    		u = new User(userId);
    		userList.put(userId,u);
    	}
    	u.post(mediaId);
    }

    /** Retrieve the 10 most recent media ids in the user's media feed.
     * Each media must be posted by the user herself or by someone the user follows
     * Media must be ordered from most recent to least recent. */
    public List<Integer> getMediaFeed(int userId) {
    	//NOTE: Check if user exists
        if(userList.containsKey(userId)){
        	User u = userList.get(userId);
        	return u.getFeed();
        }
        //NOTE: if user does not exist
        return Collections.EMPTY_LIST;
    }

    /** A Follower follows a followed. Nothing happens if invalid */
    public void follow(int followerId, int followedId) {
    	//NOTE: Check if both users exist
    	if(userList.containsKey(followerId) && userList.containsKey(followedId)){
    		//NOTE: u1 follows u2
    		User u1 = userList.get(followerId);
    		User u2 = userList.get(followedId);
    		u1.follow(u2);
    	}
    }

    /** A Follower unfollows a followed. Nothing happens if invalid */
    public void unfollow(int followerId, int followedId) {
    	//NOTE: Check if both users exist
    	if(userList.containsKey(followerId) && userList.containsKey(followedId)){
    		//NOTE: u1 unfollows u2
    		User u1 = userList.get(followerId);
    		User u2 = userList.get(followedId);
    		u1.unFollow(u2);
    		
    	}
    }
}


