package org.container;

import jakarta.servlet.ServletContext;

public interface AppInit {

  void onStartup(ServletContext servletContext);

}
