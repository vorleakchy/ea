package edu.mum.dao;

import edu.mum.domain.UserCredentials;

public interface CredentialsDao extends GenericDao<UserCredentials> {
	public UserCredentials findByUserName(String userName);

 	}
