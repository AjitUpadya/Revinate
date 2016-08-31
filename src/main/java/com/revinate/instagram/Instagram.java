package com.revinate.instagram;

import java.util.*;
import java.util.Collections;
import java.util.List;

/**
 * Your Instagram object will be instantiated and called as such:
 * Instagram instagram = new Instagram();
 * instagram.postMedia(userId,mediaId);
 * List<Integer> feed = instagram.getMediaFeed(userId);
 * instagram.follow(followerId,followedId);
 * instagram.unfollow(followerId,followedId);
 */

public class Instagram {

	// map with key: userId and value: User object
	Map<Integer, User> map;
	// list of posts with insertion order maintained
	List<Post> posts; 
	
    /** Initialize your data structure here. */
    public Instagram() {
    	map = new HashMap<Integer, User>();
    	posts = new ArrayList<Post>();
    }

    /** Add a new media. */
    public void postMedia(int userId, int mediaId) {
    	if(!map.containsKey(userId)) {
    		User user = new User();
    		map.put(userId, user);
    	}
    	posts.add(new Post(userId, mediaId));
    }

    /** Retrieve the 10 most recent media ids in the user's media feed.
     * Each media must be posted by the user herself or by someone the user follows
     * Media must be ordered from most recent to least recent. */
    public List<Integer> getMediaFeed(int userId) {
    	if(!map.containsKey(userId))
    		return Collections.emptyList();
    	List<Integer> res = new ArrayList<Integer>();
    	List<Integer> followsList = map.get(userId).getFollows();
    	int j = 0;
    	for(int i = posts.size()-1; i >= 0; i--) {
    		Post post = posts.get(i);
    		if((followsList != null && followsList.contains(post.userId)) || post.userId == userId) {
    			res.add(post.mediaId);
    			if(++j == 10)
    				break;
    		}
    	}
    	return res;
    }

    /** A Follower follows a followed. Nothing happens if invalid */
    public void follow(int followerId, int followedId) {
    	if(!map.containsKey(followerId))
    		return;
    	
    	// creates the user object of the followed user id if it does not exist
    	if(!map.containsKey(followedId)) {
    		User user = new User();
    		map.put(followedId, user);
    	}
    	List<Integer> followers = map.get(followerId).getFollows();
    	if(followers == null) {
    		followers = new ArrayList<Integer>();
    		followers.add(followedId);
    		map.get(followerId).setFollows(followers);
    	} else 
    		followers.add(followedId);
    }

    /** A Follower unfollows a followed. Nothing happens if invalid */
    public void unfollow(int followerId, int followedId) {
    	// if the follower or the followed user id is invalid, then exit
    	if(!map.containsKey(followerId) || !map.containsKey(followedId))
    		return;
    	List<Integer> followers = map.get(followerId).getFollows();
    	if(followers != null) {
    		Iterator<Integer> iter = followers.iterator();
    		while(iter.hasNext()) {
    			if(iter.next() == followedId) {
    				iter.remove();
    				break;
    			}
    		}
    	}
    }
}

/**
 * @author ajit
 * User class to keep track of the list of other users that are being followed
 */
class User {
	
	List<Integer> follows;
	int userId;
	
	public List<Integer> getFollows() {
		return follows;
	}
	public void setFollows(List<Integer> followers) {
		this.follows = followers;
	}
	public int getFeed() {
		return userId;
	}
	public void setFeed(int id) {
		this.userId = id;
	}
}

/**
 * @author ajit
 * A simple POJO to hold the posts made by users
 */
class Post {
	
	int userId;
	int mediaId;
	
	Post(int userId, int mediaId) {
		this.userId = userId;
		this.mediaId = mediaId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int postId) {
		this.mediaId = postId;
	}
}
