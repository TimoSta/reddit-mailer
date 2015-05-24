package com.timostaudinger.dailydose.admin;

import com.timostaudinger.dailydose.exception.RedditLoadException;
import com.timostaudinger.dailydose.reddit.Subreddit;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.Sorting;
import net.dean.jraw.paginators.SubredditPaginator;
import net.dean.jraw.paginators.TimePeriod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loadtest", urlPatterns = {"/test/load"})
public class LoadTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SubredditPaginator getMotivated = Subreddit.getPaginator("getmotivated", 10, TimePeriod.ALL, Sorting.TOP);

        for (Submission submission : getMotivated.next()) {
            response.getWriter().println(submission.getTitle());
        }

        response.getWriter().println("\n-----------------\n");

        try {
            String dailyTop = Subreddit.getDailyTopOf("getmotivated").getTitle();
            response.getWriter().println(dailyTop);
        } catch (RedditLoadException e) {
            response.getWriter().println(e.getMessage());
        }

    }
}