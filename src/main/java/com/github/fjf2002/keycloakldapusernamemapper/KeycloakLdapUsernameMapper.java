package com.github.fjf2002.keycloakldapusernamemapper;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.reflection.Property;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.idm.model.LDAPObject;
import org.keycloak.storage.ldap.mappers.UserAttributeLDAPStorageMapper;

/**
 * Uses the LDAP mail attribute to set the keycloak username.
 * Truncates at the '@' character, i. e. "user@domain.com" becomes "user".
 * 
 * Since most of this code is just inherited from UserAttributeLDAPStorageMapper,
 * you MUST set
 * 
 *  * "User Model Attribute" to username,
 *  * "LDAP Attribute" to mail.
 *  
 * in the GUI.
 */
public class KeycloakLdapUsernameMapper extends UserAttributeLDAPStorageMapper {

	private static final Logger logger = Logger.getLogger(KeycloakLdapUsernameMapper.class);

	public KeycloakLdapUsernameMapper(ComponentModel mapperModel, LDAPStorageProvider ldapProvider) {
		super(mapperModel, ldapProvider);
	}

	protected String transformAttribute(String attributeValue) {
		final int end = attributeValue.indexOf("@");

		if (end != -1) {
			attributeValue = attributeValue.substring(0, end);
		}

		return attributeValue.toLowerCase();
	}

	@Override
	protected void setPropertyOnUserModel(Property<Object> userModelProperty, UserModel user, String ldapAttrValue) {
		if (ldapAttrValue == null) {
			userModelProperty.setValue(user, null);
		} else {
			final Class<Object> clazz = userModelProperty.getJavaClass();

			if (String.class.equals(clazz)) {
				ldapAttrValue = transformAttribute(ldapAttrValue);
				userModelProperty.setValue(user, ldapAttrValue);
			} else {
				logger.warnf("Don't know how to set the property '%s' on user '%s' . Value of LDAP attribute is '%s' ",
						userModelProperty.getName(), user.getUsername(), ldapAttrValue.toString());
			}
		}
	}

	@Override
	public void onRegisterUserToLDAP(LDAPObject ldapUser, UserModel localUser, RealmModel realm) {
		throw new UnsupportedOperationException();
	}
}
