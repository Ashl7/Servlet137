
package util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Arash
 * A listener to count the number of active sessions
 */
public class ServletSessionCounter implements HttpSessionListener{
    private static int sessionCount; 
    
    public int getActiveSession()
    {
        return sessionCount;
    }
    
    @Override
    public void sessionCreated(HttpSessionEvent e) 
    {   
        sessionCount++;  
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent e) 
    {
        if(sessionCount > 0)
            sessionCount--;
    }
}
