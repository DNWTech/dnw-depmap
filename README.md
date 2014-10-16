DNW-depmap
=============

DNW-depmap is an Eclipse plug-in project to create Java code dependency map,
which can be used by developers to help them easily find what's the influence of arbitrary code modifications.

Development Environment
-----------------------
- JDK 1.7 (Neo4j graph database's prerequisites)
- Eclipse 3.7 (code name Indigo)
  - Import [codetemplates.xml](https://github.com/manbaum/dnw-depmap/blob/develop/codetemplates.xml) into ```Preferences|Java|Code Style|Code Templates```.
  - Import [codestyle.xml](https://github.com/manbaum/dnw-depmap/blob/develop/codestyle.xml) into ```Preferences|Java|Code Style|Formatter```.
  - Format the code before commit. Thanks.

Getting Start
-------------
1. Download Neo4j v2.1.5 from [the Neo4j site](http://neo4j.com/download/).
2. Follow [these instructions](http://neo4j.com/docs/2.1.5/deployment.html) to install and deploy a local Neo4j server.
   But do *NOT* start the server at now!
3. Fork this project and import it into Eclipse (the *first* Eclipse).
4. Configure the value of variable ```DBPATH``` in file ```com.dnw.depmap.Activator.java``` to the database store directory
   of the local Neo4j server.
5. Build and run this project as an ```Eclipse Application``` (the *second* Eclipse).
6. In the second Eclipse, import a Java project which will be analyzed into the workspace.
7. Build the imported Java project to make sure these's no syntax error.
8. In the ```Package Explorer``` window, select one or more Java elements (may be Java files, packages,
   or even the Java project), right click on them and select ```Analyze Dependency``` from the pop-up context menu.
9. Wait for done. (If there are a lot of Java files in the selection, it may be a long moment.)
10. Startup the local Neo4j server, open a browser and navigate to [http://localhost:7474/browser](http://localhost:7474/browser).
11. Write Neo4j Cypher queries to manipulate the data generated by this program.
    e.g. ```match p=((:Class)-[:Declares]->(:Method)-[:Invokes]->(:Method)<-[:Declares]-(:Class {name:"java.lang.Object"})) return p```.
    (Refer to Neo4j [Cypher Refcard 2.1.5](http://neo4j.com/docs/2.1.5/refcard/).)
12. If you want to select some other Java files and re-generating the map data, please shutdown the local Neo4j server first,
    when generating is done, you can restart the Neo4j server and do some manipulations.
13. Another thing should be aware of is that the newly generated data will be mixed with the old data.
    If you want to clean all old data, please excute some appropriate Cypher statements,
    like ```match ()-[r]-() match (n) delete r, n```.

Data Model
----------
- Node labeled ```Type``` will also be labeled either ```Class``` or ```Interface```.
- Node labeled ```Class``` denotes a Java class.
  - Property ```name```: The qualified name of the class without type parameter. e.g. ```'java.util.ArrayList'```.
  - Property ```caption```: The simple name (no package name) of the class without type parameter. e.g. ```'ArrayList'```.
  - Property ```extends```: The qualified name of its superclass. e.g. ```'java.lang.Object'```.
  - Property ```implements```: An array contains all names of interfaces which the class implements. e.g. ```['java.util.List']```.
- Node labeled ```Interface``` denotes a Java interface.
  - Property ```name```: The qualified name of the interface without type parameter. e.g. ```'java.util.List'```.
  - Property ```caption```: The simple name (no package name) of the interface without type parameter. e.g. ```'List'```.
  - Property ```extends```: An array contains all names of interfaces which extends. e.g. ```['java.util.Collection']```.
- Relation type ```Extends``` denotes the class inheritance happened between two ```Class``` nodes, or the interface inheritance happened between two ```Interface``` nodes.
- Relation type ```Implements``` denotes the interface implementation between a ```Class``` node and a ```Interface``` node.
- Node labeled ```Method``` denotes a Java method.
  - Property ```name```: Which consists of two parts: 1) the qualified name of its declaring class, 2) the method declaring, made by the method name and all the arguments' type names in parentheses.
    * For a static method, uses ```'/'``` as the delimiter. e.g. ```'java.util.Arrays/asList(java.lang.Object[])'```.
    * For a non-static method, uses ```'#'``` as the delimiter. e.g. ```'java.lang.Object#wait()'```.
  - Property ```caption```: Which concatenates the method name and a pair of parentheses. e.g. ```'asList()'```.
- Relation type ```Declares``` denotes the method declaration, the from side should be a ```Type``` node, and the to side should be a ```Method``` node.
- Relation type ```Invokes``` denotes the method invocation, it happens between two ```Method``` nodes.
- Relation type ```Overrides``` denotes the method override, it happens between two ```Method``` nodes. There are two kinds of override: Superclass override and Interface override.
  - Superclass override: The method overrides a method which exists in the superclass of the declaring class.
  - Interface override: The method overrides a method which exists in one or more interfaces that the declaring class implements.


License
-------
Copyright (c) 2014 DNW Technologies and others.<br/>
All rights reserved. This program and the accompanying materials<br/>
are made available under the terms of the Eclipse Public License v1.0<br/>
which accompanies this distribution, and is available at<br/>
http://www.eclipse.org/legal/epl-v10.html
