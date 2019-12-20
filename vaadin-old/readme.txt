====
    Copyright 2018 Mike Hummel

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
====


docker run -it --name vaadin8 \
 -h vaadin \
 -p 8181:8181 \
 -v ~/.m2:/home/user/.m2 \
 mhus/apache-karaf:4.2.6_01

 
toolsversion=1.6.3-SNAPSHOT
feature:repo-add mvn:de.mhus.osgi/mhu-karaf-feature/${toolsversion}/xml/features

feature:repo-add cxf 3.3.1
feature:install deployer/4.2.6 aries-blueprint/4.2.6 cxf-core/3.3.1 openjpa3/3.0.0

feature:install mhu-osgi-vaadin

