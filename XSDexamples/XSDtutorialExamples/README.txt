Samples taken from the XML tutorial:
XML Schema Part 0: Primer (W3C) - http://www.w3.org/TR/xmlschema-0/
The original samples have been slightly modified:

purchaseOrder.xml	initial sample purchase order xml file (original)
purchaseOrder.xsd	initial purchase order schema (original)

4Q99_nn.xml			sample quarterly report xml file without namespaces
report_nn.xsd		schema file without namespaces and single-file

address.xsd			schema with types regarding addresses
ipo.xsd				international purchase order schema (includes address.xsd)
ipo.xml				sample xml file for the above schema

4Q99.xml			sample quarterly report xml file (original)
report.xsd			schema for the above xml file (imports ipo.xsd, original) 

The xml files with the _v suffix are modified versions of the
corresponding ones, where a reference to the corresponding schema
has been added and content has been modified so that it results
valid.

