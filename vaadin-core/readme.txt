
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

