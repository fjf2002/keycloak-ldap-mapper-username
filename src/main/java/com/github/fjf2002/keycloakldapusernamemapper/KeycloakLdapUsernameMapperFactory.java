package com.github.fjf2002.keycloakldapusernamemapper;

import org.keycloak.component.ComponentModel;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapper;
import org.keycloak.storage.ldap.mappers.LDAPConfigDecorator;
import org.keycloak.storage.ldap.mappers.UserAttributeLDAPStorageMapperFactory;

public class KeycloakLdapUsernameMapperFactory extends UserAttributeLDAPStorageMapperFactory
		implements LDAPConfigDecorator {

	public static final String PROVIDER_ID = "fjf2002-username-mapper";

	@Override
	public String getHelpText() {
		return "Uses the mail address up to the @ sign for keycloak username.";
	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	protected AbstractLDAPStorageMapper createMapper(ComponentModel mapperModel,
			LDAPStorageProvider federationProvider) {

		return new KeycloakLdapUsernameMapper(mapperModel, federationProvider);
	}
}
