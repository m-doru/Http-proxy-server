# Http-proxy-server
Advanced object oriented programming lab project.

#Properties
<ul>
  <li>HTTP only</li>
  <li>Supports GET, POST and other methods</li>
  <li>Supports HTTP video streaming</li>
  <li>Supports logging of traffic from both the client and the server</li>
  <li>No keep alive. Opens a new connection to target server every time a client asks for it</li>
</ul>

#Building using Maven
<ol>
  <li>git clone https://github.com/m-doru/Http-proxy-server.git</li>
  <li>cd Http-proxy-server</li>
  <li>mvn [chosen build phase]</li>
</ol>

#Usage
<p>After it was built with Maven to package phase, go to target directory and run:</p>
<p>java -jar HTTP-Proxy-0.1.jar [name] [port] [backlog]</p>
By default:
<ol>
  <li>name = noiceprxy</li>
  <li>port = 32165 </li>
  <li>backlog = 500</li>
</ol>
