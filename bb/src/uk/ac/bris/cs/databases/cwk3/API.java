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


    private final Connection c;
    public API(Connection c) {
        this.c = c;
    }

    @Override
    public Result<Map<String, String>> getUsers() {
        return Person.getUsers(c);
    }

    @Override
    public Result<PersonView> getPersonView(String username) {
        return Person.getPersonView(c, username);
    }

    @Override
    public Result<List<SimpleForumSummaryView>> getSimpleForums() {
    	return Forum.getSimpleSummary(c);
    }

    @Override
    public Result<Integer> countPostsInTopic(long topicId) {
    	return Topic.getCount(c, topicId);
    }

    @Override
    public Result<List<PersonView>> getLikers(long topicId) {
        return Topic.getLikers(c, topicId);
    }

    @Override
    public Result<SimpleTopicView> getSimpleTopic(long topicId) {
        return Topic.getSimpleTopic(c, topicId);
    }

    @Override
    public Result<PostView> getLatestPost(long topicId) {
        return Topic.getLatestPost(c, topicId);
    }

    @Override
    public Result<List<ForumSummaryView>> getForums() {
        return Forum.getSummary(c);
    }

    @Override
    public Result createForum(String title) {
        	return Forum.addForum(c,title);
    }

    @Override
    public Result createPost(long topicId, String username, String text) {
        return Post.createPost(c, topicId, username, text);
    }

    @Override
    public Result addNewPerson(String name, String username, String studentId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result<ForumView> getForum(long id) {
        return Forum.getDetailedForum(c, id);
    }

    @Override
    public Result<TopicView> getTopic(long topicId, int page) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Result likeTopic(String username, long topicId, boolean like) {
        return Topic.likeTopic(c, username, topicId, like);
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
