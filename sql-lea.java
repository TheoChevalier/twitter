try {
	s.executeQuery(query);
} catch(Exception e) {
	// sinon on l'a cree
	s.execute("DROP TABLE MESSAGES");
	s.execute("create table MESSAGES  ( " +
			" idM int auto_increment NOT NULL PRIMARY KEY, " +
			" contenuM VARCHAR( 256 ) , " +
			" dateM DATE," +
			" heureM TIME," +
			" locM VARCHAR (50))");
}
try {
	s.executeQuery(query);
} catch(Exception e) {
	// sinon on l'a cree
	s.execute("DROP TABLE EST_ABONNE");
	s.execute("create table EST_ABONNE  ( " +
			" idUSuiveur int FOREIGN KEY REFERENCES UTILISATEUR(idU), " +
			" idUSuivi int FOREIGN KEY REFERENCES UTILISATEUR(idU))" + 
			"CONSTRAINT pk_est_abonne PRIMARY KEY (idUSuiveur, idUSuivi)");
}
