/**   */
package org.saber.avalon.common.shiro.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.MergableAuthenticationInfo;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.MutablePrincipalCollection;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**   
 * <p>类名称: AuthenInfo </p> 
 * <p>描述: TODO  </p>
 * <p>创建时间 : 2019年2月12日 下午2:26:42 </p>
 * @author lijunliang
 * @version 1.0
 * 
 */
public class UserPwdAuthenInfo implements MergableAuthenticationInfo, SaltedAuthenticationInfo{
	/** */
	private static final long serialVersionUID = -98720760799178567L;
	   /**
     * The principals identifying the account associated with this AuthenticationInfo instance.
     */
    protected PrincipalCollection principals;
    /**
     * The credentials verifying the account principals.
     */
    protected Object credentials;

    /**
     * Any salt used in hashing the credentials.
     *
     * @since 1.1
     */
    protected ByteSource credentialsSalt;

    /**
     * Default no-argument constructor.
     */
    public UserPwdAuthenInfo() {
    }

    /**
     * Constructor that takes in a single 'primary' principal of the account and its corresponding credentials,
     * associated with the specified realm.
     * <p/>
     * This is a convenience constructor and will construct a {@link PrincipalCollection PrincipalCollection} based
     * on the {@code principal} and {@code realmName} argument.
     *
     * @param principal   the 'primary' principal associated with the specified realm.
     * @param credentials the credentials that verify the given principal.
     * @param realmName   the realm from where the principal and credentials were acquired.
     */
    public UserPwdAuthenInfo(Object principal, Object credentials, String realmName) {
        this.principals = new SimplePrincipalCollection(principal, realmName);
        this.credentials = credentials;
    }

    /**
     * Constructor that takes in a single 'primary' principal of the account, its corresponding hashed credentials,
     * the salt used to hash the credentials, and the name of the realm to associate with the principals.
     * <p/>
     * This is a convenience constructor and will construct a {@link PrincipalCollection PrincipalCollection} based
     * on the <code>principal</code> and <code>realmName</code> argument.
     *
     * @param principal         the 'primary' principal associated with the specified realm.
     * @param hashedCredentials the hashed credentials that verify the given principal.
     * @param credentialsSalt   the salt used when hashing the given hashedCredentials
     * @param realmName         the realm from where the principal and credentials were acquired.
     * @see org.apache.shiro.authc.credential.HashedCredentialsMatcher HashedCredentialsMatcher
     * @since 1.1
     */
    public UserPwdAuthenInfo(Object principal, Object hashedCredentials, ByteSource credentialsSalt, String realmName) {
        this.principals = new SimplePrincipalCollection(principal, realmName);
        this.credentials = hashedCredentials;
        this.credentialsSalt = credentialsSalt;
    }

    /**
     * Constructor that takes in an account's identifying principal(s) and its corresponding credentials that verify
     * the principals.
     *
     * @param principals  a Realm's account's identifying principal(s)
     * @param credentials the accounts corresponding principals that verify the principals.
     */
    public UserPwdAuthenInfo(PrincipalCollection principals, Object credentials) {
        this.principals = new SimplePrincipalCollection(principals);
        this.credentials = credentials;
    }

    /**
     * Constructor that takes in an account's identifying principal(s), hashed credentials used to verify the
     * principals, and the salt used when hashing the credentials.
     *
     * @param principals        a Realm's account's identifying principal(s)
     * @param hashedCredentials the hashed credentials that verify the principals.
     * @param credentialsSalt   the salt used when hashing the hashedCredentials.
     * @see org.apache.shiro.authc.credential.HashedCredentialsMatcher HashedCredentialsMatcher
     * @since 1.1
     */
    public UserPwdAuthenInfo(PrincipalCollection principals, Object hashedCredentials, ByteSource credentialsSalt) {
        this.principals = new SimplePrincipalCollection(principals);
        this.credentials = hashedCredentials;
        this.credentialsSalt = credentialsSalt;
    }


    public PrincipalCollection getPrincipals() {
        return principals;
    }

    /**
     * Sets the identifying principal(s) represented by this instance.
     *
     * @param principals the indentifying attributes of the corresponding Realm account.
     */
    public void setPrincipals(PrincipalCollection principals) {
        this.principals = principals;
    }

    public Object getCredentials() {
        return credentials;
    }

    /**
     * Sets the credentials that verify the principals/identity of the associated Realm account.
     *
     * @param credentials attribute(s) that verify the account's identity/principals, such as a password or private key.
     */
    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    /**
     * Returns the salt used to hash the credentials, or {@code null} if no salt was used or credentials were not
     * hashed at all.
     * <p/>
     * Note that this attribute is <em>NOT</em> handled in the
     * {@link #merge(AuthenticationInfo) merge} method - a hash salt is only useful within a single realm (as each
     * realm will perform it's own Credentials Matching logic), and once finished in that realm, Shiro has no further
     * use for salts.  Therefore it doesn't make sense to 'merge' salts in a multi-realm scenario.
     *
     * @return the salt used to hash the credentials, or {@code null} if no salt was used or credentials were not
     *         hashed at all.
     * @since 1.1
     */
    public ByteSource getCredentialsSalt() {
        return credentialsSalt;
    }

    /**
     * Sets the salt used to hash the credentials, or {@code null} if no salt was used or credentials were not
     * hashed at all.
     * <p/>
     * Note that this attribute is <em>NOT</em> handled in the
     * {@link #merge(AuthenticationInfo) merge} method - a hash salt is only useful within a single realm (as each
     * realm will perform it's own Credentials Matching logic), and once finished in that realm, Shiro has no further
     * use for salts.  Therefore it doesn't make sense to 'merge' salts in a multi-realm scenario.
     *
     * @param salt the salt used to hash the credentials, or {@code null} if no salt was used or credentials were not
     *             hashed at all.
     * @since 1.1
     */
    public void setCredentialsSalt(ByteSource salt) {
        this.credentialsSalt = salt;
    }

    /**
     * Takes the specified <code>info</code> argument and adds its principals and credentials into this instance.
     *
     * @param info the <code>AuthenticationInfo</code> to add into this instance.
     */
    @SuppressWarnings("unchecked")
    public void merge(AuthenticationInfo info) {
        if (info == null || info.getPrincipals() == null || info.getPrincipals().isEmpty()) {
            return;
        }

        if (this.principals == null) {
            this.principals = info.getPrincipals();
        } else {
            if (!(this.principals instanceof MutablePrincipalCollection)) {
                this.principals = new SimplePrincipalCollection(this.principals);
            }
            ((MutablePrincipalCollection) this.principals).addAll(info.getPrincipals());
        }

        //only mess with a salt value if we don't have one yet.  It doesn't make sense
        //to merge salt values from different realms because a salt is used only within
        //the realm's credential matching process.  But if the current instance's salt
        //is null, then it can't hurt to pull in a non-null value if one exists.
        //
        //since 1.1:
        if (this.credentialsSalt == null && info instanceof SaltedAuthenticationInfo) {
            this.credentialsSalt = ((SaltedAuthenticationInfo) info).getCredentialsSalt();
        }

        Object thisCredentials = getCredentials();
        Object otherCredentials = info.getCredentials();

        if (otherCredentials == null) {
            return;
        }

        if (thisCredentials == null) {
            this.credentials = otherCredentials;
            return;
        }

        if (!(thisCredentials instanceof Collection)) {
            Set<Object> newSet = new HashSet<Object>();
            newSet.add(thisCredentials);
            setCredentials(newSet);
        }

        // At this point, the credentials should be a collection
        Collection<Object> credentialCollection = (Collection<Object>) getCredentials();
        if (otherCredentials instanceof Collection) {
            credentialCollection.addAll((Collection<?>) otherCredentials);
        } else {
            credentialCollection.add(otherCredentials);
        }
    }

    /**
     * Returns <code>true</code> if the Object argument is an <code>instanceof SimpleAuthenticationInfo</code> and
     * its {@link #getPrincipals() principals} are equal to this instance's principals, <code>false</code> otherwise.
     *
     * @param o the object to compare for equality.
     * @return <code>true</code> if the Object argument is an <code>instanceof SimpleAuthenticationInfo</code> and
     *         its {@link #getPrincipals() principals} are equal to this instance's principals, <code>false</code> otherwise.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleAuthenticationInfo)) return false;

        UserPwdAuthenInfo that = (UserPwdAuthenInfo) o;

        //noinspection RedundantIfStatement
        if (principals != null ? !principals.equals(that.principals) : that.principals != null) return false;

        return true;
    }

    /**
     * Returns the hashcode of the internal {@link #getPrincipals() principals} instance.
     *
     * @return the hashcode of the internal {@link #getPrincipals() principals} instance.
     */
    public int hashCode() {
        return (principals != null ? principals.hashCode() : 0);
    }

    /**
     * Simple implementation that merely returns <code>{@link #getPrincipals() principals}.toString()</code>
     *
     * @return <code>{@link #getPrincipals() principals}.toString()</code>
     */
    public String toString() {
        return principals.toString();
    }


	
}
