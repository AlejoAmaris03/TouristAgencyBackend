package com.springboot.tourism_agency_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.springboot.tourism_agency_backend.models.JobTitle;
import com.springboot.tourism_agency_backend.models.PaymentMethod;
import com.springboot.tourism_agency_backend.models.Role;
import com.springboot.tourism_agency_backend.models.Users;
import com.springboot.tourism_agency_backend.repositories.JobTitleRepository;
import com.springboot.tourism_agency_backend.repositories.PaymentMethodRepository;
import com.springboot.tourism_agency_backend.repositories.RoleRepository;
import com.springboot.tourism_agency_backend.repositories.UserRepository;

@Configuration

public class DataInitializer {
    private ApplicationContext applicationContext;

    DataInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepo) {
        // Default roles
        return roles -> {
            if(roleRepo.findByName("ROLE_ADMIN") == null)
                roleRepo.save(new Role(0, "ROLE_ADMIN"));

            if(roleRepo.findByName("ROLE_CUSTOMER") == null)
                roleRepo.save(new Role(0, "ROLE_CUSTOMER"));

            if(roleRepo.findByName("ROLE_EMPLOYEE") == null)
                roleRepo.save(new Role(0, "ROLE_EMPLOYEE"));
        };
    }

    @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner initUsers(UserRepository userRepo) {
        return users -> {
            // Default admin acc
            if(userRepo.findUserByDniOrUsername(112223344L, "admin") == null) {
                userRepo.save(new Users(
                    0,
                    "Admin name",
                    "Admin surname",
                    112223344L,
                    "admin@example.com",
                    "admin",
                    applicationContext.getBean(BCryptPasswordEncoder.class).encode("admin"),
                    new Role(1, "ROLE_ADMIN")
                ));
            }

            // Acc. for an employee
            if(userRepo.findUserByDniOrUsername(998887766L, "lola") == null) {
                userRepo.save(new Users(
                    0,
                    "Lola",
                    "Smith",
                    998887766L,
                    "lola_employee@example.com",
                    "lola",
                    applicationContext.getBean(BCryptPasswordEncoder.class).encode("123"),
                    new Role(2, "ROLE_CUSTOMER")
                ));
            }
        };
    }

    @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner initJobTitles(JobTitleRepository jobTitleRepo) {
        // Adding job titles
        return jobTitles -> {
            if(jobTitleRepo.findJobTitleByName("Travel Agent") == null)
                jobTitleRepo.save(new JobTitle(0, "Travel Agent"));

            if(jobTitleRepo.findJobTitleByName("Seller") == null)
                jobTitleRepo.save(new JobTitle(0, "Seller"));
        };
    }

    @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner initPaymentMethods(PaymentMethodRepository paymentMethodRepo) {
        // Adding job titles
        return paymentMethods -> {
            if(paymentMethodRepo.findPaymentMethodByName("Cash") == null)
                paymentMethodRepo.save(new PaymentMethod(0, "Cash", 0D));

            if(paymentMethodRepo.findPaymentMethodByName("Debit card") == null)
                paymentMethodRepo.save(new PaymentMethod(0, "Debit card", 3D));

            if(paymentMethodRepo.findPaymentMethodByName("Credit card") == null)
                paymentMethodRepo.save(new PaymentMethod(0, "Credit card", 9D));

            if(paymentMethodRepo.findPaymentMethodByName("Digital wallet") == null)
                paymentMethodRepo.save(new PaymentMethod(0, "Digital wallet", 0D));

            if(paymentMethodRepo.findPaymentMethodByName("Transfer") == null)
                paymentMethodRepo.save(new PaymentMethod(0, "Transfer", 2.45));
        };
    }
}
