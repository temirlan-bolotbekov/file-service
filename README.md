
# File-System implement REST API only Servlets and Pattern SPI

For run application you need:
1. Pull dependencies
2. Run command mvn clean install
3. Run jetty server command mvn jetty:run(from file-client module)

if you get error "not access to protected method..."
add VM -options (--add-opens java.base/java.lang=ALL-UNNAMED) in run configuration in IDE