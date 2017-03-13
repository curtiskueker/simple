package org.curtis.test;

import org.apache.commons.collections4.iterators.IteratorEnumeration;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockedHttpServletRequest extends HttpServletRequestWrapper {
    private HttpServletRequest mockedRequest;
    private HttpSession session;

    private Map<String, Object> requestAttributes = new ConcurrentHashMap<>();
    private Map<String, String[]> requestParameters = new ConcurrentHashMap<>();

    public MockedHttpServletRequest(HttpSession session) {
        super(Mockito.mock(HttpServletRequest.class));

        this.mockedRequest = (HttpServletRequest) getRequest();
        this.session = session;

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgumentAt(0, String.class);
                Object value = invocation.getArgumentAt(1, Object.class);
                if (key != null && value != null) requestAttributes.put(key, value);
                return null;
            }
        }).when(mockedRequest).setAttribute(Mockito.anyString(), Mockito.anyObject());

        // Mock getAttributeNames
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Enumeration enumeration = new IteratorEnumeration(requestAttributes.keySet().iterator());
                return enumeration;
            }
        }).when(mockedRequest).getAttributeNames();

        // Mock getAttribute
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgumentAt(0, String.class);
                return requestAttributes.get(key);
            }
        }).when(mockedRequest).getAttribute(Mockito.anyString());

        // Mock removeAttribute
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgumentAt(0, String.class);
                return requestAttributes.remove(key);
            }
        }).when(mockedRequest).removeAttribute(Mockito.anyString());

        // Mock getParameter
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgumentAt(0, String.class);
                return (requestParameters.containsKey(key)) ? requestParameters.get(key)[0] : null;
            }
        }).when(mockedRequest).getParameter(Mockito.anyString());

        // Mock getParameterValues
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgumentAt(0, String.class);
                return requestParameters.get(key);
            }
        }).when(mockedRequest).getParameterValues(Mockito.anyString());

        // Mock getParameterNames
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Enumeration enumeration = new IteratorEnumeration(requestParameters.keySet().iterator());
                return enumeration;
            }
        }).when(mockedRequest).getParameterNames();

        // Mock getParameterMap
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return requestParameters;
            }
        }).when(mockedRequest).getParameterMap();
    }

    public void setParameter(String name, String value) {
        String[] values = value.split(",");

        requestParameters.put(name, values);
    }

    @Override
    public String getParameter(String name) {
        return mockedRequest.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return mockedRequest.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return mockedRequest.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        return mockedRequest.getParameterValues(name);
    }

    @Override
    public Object getAttribute(String name) {
        return mockedRequest.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return mockedRequest.getAttributeNames();
    }

    @Override
    public void setAttribute(String name, Object o) {
        mockedRequest.setAttribute(name, o);
    }

    @Override
    public void removeAttribute(String name) {
        mockedRequest.removeAttribute(name);
    }

    @Override
    public boolean isSecure() { return true; }

    @Override
    public HttpSession getSession() {
        return session;
    }

    @Override
    public HttpSession getSession(boolean bool) {
        return session;
    }
}
