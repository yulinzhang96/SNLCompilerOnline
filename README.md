# SNLCompilerOnline
The Java Web version of the original SNL Compiler

Project Description:
• Implemented the lexical analyzer that can transform SNL (similar to Pascal) codes into tokens by means by intrducing deterministic finite automaton

• Implemented the syntax parser that accepts tokens and outputs an abstract syntax tree by using LL(1)
analysis matrix and data structures like ArrayList, HashMap, and Stack

• Deployed on Azure and AWS Elastic Beanstalk

Deployment:
• Export the project to a WAR file
• Upload directly on AWS Elastic Beanstalk
• Need to move files to "/webapp/ROOT/" on Azure

Links:

• https://snlcompileronline.azurewebsites.net/

• http://snlcompiler-env.eba-shcga2pj.us-east-2.elasticbeanstalk.com/
