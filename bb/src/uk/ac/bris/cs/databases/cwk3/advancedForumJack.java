	/**
     * Get the "main page" containing a list of forums ordered alphabetically
     * by title. Advanced version.
     * @return the list of all forums.
     */

    //First create query to grab all forums, need to carry out second query on each separate forum
    public static Result<List<AdvancedForumSummaryView>> getAdvancedForums(Connection c);
    {
        ResultSet rst, secondRst, thirdRst, fourthRst; 
        String forumTitle, creatorUserName, creatorName, authorTopicTitle, lastPostName;
        int likes, postCount, lastPostTime;
        long created, authorTopicID;
        ArrayList<AdvancedForumSummaryView> forumList = new ArrayList<AdvancedForumSummaryView>();  
        try (PreparedStatement pstmt = c.prepareStatement(simpleForumSummaryViewStatement)){                       
            rst = pstmt.executeQuery();
            //Outer while loop which will work its way through the forum list
            while (rst.next()) { 
                forumID = rst.getLong("id");
                forumTitle = rst.getString("title");
                //Find out the topic author name, username and when it was created (first person to post in a topic)
                try (PreparedStatement secondPstmt = c.prepareStatement(getAdvancedForumsSQL)) { 
                    secondPstmt.setLong(1, forumID);                   
                    secondRst = secondPstmt.executeQuery();
                    if (secondRst.next()) {
                        creatorUserName = secondRst.getString("Person.username");
                        creatorName = secondRst.getString("Person.name");
                        created = secondRst.getLong("Post.postedAt");  
                        authorTopicTitle = secondRst.getString("Topic.title"); 
                        authorTopicID = secondRst.getLong("Topic.id");                   
                    }
                    //Calculates the number of likes for this topic
                    try (PreparedStatement thirdPstmt = c.prepareStatement(getLikersStatement)) {
                        thirdPstmt.setLong(1, authorTopicTitle); 
                        thirdRst = thirdPstmt.executeQuery();
                        if (thirdRst.next()) {
                            likes = thirdRst.getInt(1);
                        }
                        try (PreparedStatement fourthPstmt = c.prepareStatement(latestPostSQL)) {
                            fourthPstmt.setLong(1, authorTopicID);
                            fourthRst = fourthPstmt.executeQuery();
                            if(rst.last()) {
                                postCount = rst.getRow();
                                lastPostName = rst.getString("name");
                                lastPostTime = rst.getInt("postedAt");
                            }
                            TopicSummaryView lastTopic = new TopicSummaryView(topicId, forumID, authorTopicTitle, postCount, created, 
                                                                lastPostTime, lastPostName, likes, creatorName, creatorUserName);                              
                            forumList.add(new AdvancedForumSummaryView(forumID, forumTitle, lastTopic));
                        }
                        catch (SQLException e) {            
                            return Result.fatal("Unknown error");
                        }
                    }
                    catch (SQLException e) {            
                        return Result.fatal("Unknown error");
                    }
                }
                catch (SQLException e) {            
                    return Result.fatal("Unknown error");
                }
            }
            return Result.success(forumList); 
        }
        catch (SQLException e) {            
            return Result.fatal("Unknown error");
        }
    }
