package org.example.utils;

import org.example.models.User.User;

public class Session {
      private static Session session;
      private User user;
      public Session(User user)
      {
          this.user=user;
      }
      public static Session Start_session(User user)
      {
          if(session==null)
          {
              session=new Session(user);
          }
          return session;
      }
      public void clearSession()
      {
          this.user=null;
          session=null;
      }
      public static  Session getSession()
      {

          return session;
      }
      public  User getUser()
      {
          return this.user;
      }
}
