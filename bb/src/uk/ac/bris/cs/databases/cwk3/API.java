package uk.ac.bris.cs.databases.cwk3;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.*;
import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.AdvancedForumSummaryView;
import uk.ac.bris.cs.databases.api.AdvancedForumView;
import uk.ac.bris.cs.databases.api.ForumSummaryView;
import uk.ac.bris.cs.databases.api.ForumView;
import uk.ac.bris.cs.databases.api.AdvancedPersonView;
import uk.ac.bris.cs.databases.api.PostView;
import uk.ac.bris.cs.databases.api.Result;
import uk.ac.bris.cs.databases.api.PersonView;
import uk.ac.bris.cs.databases.api.SimpleForumSummaryView;
import uk.ac.bris.cs.databases.api.SimpleTopicView;
import uk.ac.bris.cs.databases.api.TopicView;

/**
 *
 * @author csxdb
 */
public class API implements APIProvider {

	
	private final Connection c;                                                         //what is the use of this connection
    public API(Connection c) {                                                          //how to use this one
        this.c = c;
    }
   
    @Override
    public Result<Map<String, String>> getUsers() {           
			return GetUser.GetUserInfo(c);
    }

    @Override
    public Result<PersonView> getPersonView(String username) {
			return GetPersonView.getPersonView(c, username);
    }

    @Override
    public Result<List<SimpleForumSummaryView>> getSimpleForums() {
    	return simpleForumSummaryView.getSummary(c);
    }

    @Override
    public Result<Integer> countPostsInTopic(long topicId) {   	
    	return CountPostsinTopic.getCount(c, topicId);
    	///throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<List<PersonView>> getLikers(long topicId) {     
        return GetPersonView.getLikers(c, topicId);
    }

    @Override
    public Result<SimpleTopicView> getSimpleTopic(long topicId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<PostView> getLatestPost(long topicId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<List<ForumSummaryView>> getForums() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result createForum(String title) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result createPost(long topicId, String username, String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result addNewPerson(String name, String username, String studentId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<ForumView> getForum(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<TopicView> getTopic(long topicId, int page) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result likeTopic(String username, long topicId, boolean like) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result favouriteTopic(String username, long topicId, boolean fav) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result createTopic(long forumId, String username, String title, String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<List<AdvancedForumSummaryView>> getAdvancedForums() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<AdvancedPersonView> getAdvancedPersonView(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<AdvancedForumView> getAdvancedForum(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result likePost(String username, long topicId, int post, boolean like) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   }
