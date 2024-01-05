@ApplicationModule(allowedDependencies = {
        "global::enums","global::exception","global::response","global::util","global::entity",
        "global::modelmapper","global::querydsl","global::redis","members::security","global::swagger"})

package gentledog.members.orders.payment.application;

import org.springframework.modulith.ApplicationModule;