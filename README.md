## In English:
This tool was built as final work for the Master in Computer Sciente at Federal University of Rio Grande do Sul in 2013.

The software reuse faces numerous managerial, technical and cultural barriers in its adoption, and the definition of the structure of reusable software assets is one of these technical barriers. To solve this, the Reusable Asset Specification (RAS) is a de facto standard proposed by OMG. A specification such as the RAS defines and standardizes a reusable asset model, and it is the foundation for the construction and for the use of an asset repository that supports the software reuse. However, for being adopted in the practice, the RAS needs to solve its lacks through its extension and the definition of complementary information. Solving these lacks, the RAS becomes useful to help effectively in the standardization of packaging reusable assets and to guide the structure of the software reuse repository. Some previous works have already partially answered this question, but they attended very specific purposes, did not have a support tool or have not been evaluated in a real context of (re)use. This work proposes the Software Profile RAS (SW-RAS), an extension of the component Profile of RAS proposing solutions for its various lacks, including useful information and relevant artifacts pointed in the literature, based on other reusable asset models, on other RAS extensions and on the experience in the reuse process at software development. Particularly, the SW-RAS extends the categories of classification, solution, usage and related assets, whose details are described in the text. Aiming for the experimentation of the proposal through a case study, the Lavoi was developed, a reusable asset repository fully based on the SW-RAS, which is was evaluated in a real environment of reuse and software development of a large public IT company. A description of this evaluation process in real context is also presented in this paper. The main contribution of this dissertation is the proposal, the evaluation and the consolidation of an extension of RAS that attends several of its lacks and is supported by a free software tool.

## Em português:
É disponibilizado com licença de software livre (GPLv3), seu código-fonte está no repositório de projetos open source GitHub, em https://github.com/dsmoura/lavoi. Por ser livre, pode ser baixado, utilizado, adaptado e evoluído por qualquer pessoa ou entidade, sendo desenvolvido com tecnologias livres e atuais na plataforma Java Enterprise Edition. Foi utilizado o Framework MVC JavaServer Faces (JSF) com AJAX para a construção da interface web dinâmica com o usuário. Para a persistência dos dados foi utilizada a tecnologia Java Persistence API (JPA), que é compatível com diversos sistemas gerenciadores de banco de dados. O servidor de aplicação Java pode ser qualquer um compatível com JavaEE 5: Apache Tomcat, Jboss Application Server, entre outros. A plataforma de índice de pesquisa utilizada é o Apache Solr, baseado em Apache Lucene.

"Software Profile RAS: Estendendo a Padronização do Reusable Asset Specification e Construindo um Repositório de Ativos"
You can download the entire work here <https://lume.ufrgs.br/bitstream/handle/10183/87582/000911204.pdf>

Keywords: Software Reuse, Reusable Software Asset Model, Reusable Asset Specification, RAS, Reusable Asset Repository, Software Reuse Repository, Software Reuse Library

## Technologies:

### Frontend
- JSF JavaServer Faces 1.2
- Richfaces 3.3

### Backend
- Apache Commons Email
- JPA Java Persistence API 2.0
- PostgreSQL 9
- Log4j 2

### Indexing and Searching
- Apache Lucene 3.5
- Apache Solr 3.5
- Apache Tika 1.3
