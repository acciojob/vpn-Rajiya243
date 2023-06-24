package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public com.driver.model.User register(String username, String password, String countryName) throws Exception{
        //create a user of given country. The ip of the user should be "countryCode.userId" and return the user.
        //Note that the userId is created automatically by the repository layer

        if(countryName.equalsIgnoreCase("ind") || countryName.equalsIgnoreCase("usa") || countryName.equalsIgnoreCase("aus")||countryName.equalsIgnoreCase("jpn")||countryName.equalsIgnoreCase("chi")) {

            User user = new User() {
                @Override
                public boolean equals(Object another) {
                    return false;
                }

                @Override
                public String toString() {
                    return null;
                }

                @Override
                public int hashCode() {
                    return 0;
                }

                @Override
                public String getName() {
                    return null;
                }

                @Override
                public String getFullName() {
                    return null;
                }

                @Override
                public void setFullName(String s) {

                }

                @Override
                public Iterator<Group> getGroups() {
                    return null;
                }

                @Override
                public String getPassword() {
                    return null;
                }

                @Override
                public void setPassword(String s) {

                }

                @Override
                public Iterator<Role> getRoles() {
                    return null;
                }

                @Override
                public UserDatabase getUserDatabase() {
                    return null;
                }

                @Override
                public String getUsername() {
                    return null;
                }

                @Override
                public void setUsername(String s) {

                }

                @Override
                public void addGroup(Group group) {

                }

                @Override
                public void addRole(Role role) {

                }

                @Override
                public boolean isInGroup(Group group) {
                    return false;
                }

                @Override
                public boolean isInRole(Role role) {
                    return false;
                }

                @Override
                public void removeGroup(Group group) {

                }

                @Override
                public void removeGroups() {

                }

                @Override
                public void removeRole(Role role) {

                }

                @Override
                public void removeRoles() {

                }
            };
            user.setPassword(password);
            user.setUsername(username);

            Country country = new Country();

            if (countryName.equalsIgnoreCase("ind")) {
                country.setCountryName(CountryName.IND);
                country.setCode(CountryName.IND.toCode());
            }
            if (countryName.equalsIgnoreCase("usa")) {
                country.setCountryName(CountryName.USA);
                country.setCode(CountryName.USA.toCode());
            }
            if (countryName.equalsIgnoreCase("aus")) {
                country.setCountryName(CountryName.AUS);
                country.setCode(CountryName.AUS.toCode());
            }
            if (countryName.equalsIgnoreCase("jpn")) {
                country.setCountryName(CountryName.JPN);
                country.setCode(CountryName.JPN.toCode());
            }
            if (countryName.equalsIgnoreCase("chi")) {
                country.setCountryName(CountryName.CHI);
                country.setCode(CountryName.CHI.toCode());
            }

            country.setUser((com.driver.model.User) user);
            user.getUsername();
            user.getName();

            String IP = country.getCode() +"."+ userRepository3.save(user).getName();
            user.setUsername(IP);
            userRepository3.save(user);
            return (com.driver.model.User) user;
        }
        else
            throw new Exception("Country not found");


    }

    @Override
    public com.driver.model.User subscribe(Integer userId, Integer serviceProviderId) throws InterruptedException {
        User user = userRepository3.findById(userId).get();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();

        user.getRoles().wait(serviceProvider.getId());
        serviceProvider.getUsers().add((com.driver.model.User) user);

        serviceProviderRepository3.save(serviceProvider);
        return (com.driver.model.User) user;
    }
}
