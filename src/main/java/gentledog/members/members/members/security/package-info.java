@NamedInterface(value = "security")
@ApplicationModule(allowedDependencies = {
        "global::enums","global::exception","global::response","global::util","global::entity",
        "global::modelmapper","global::querydsl","global::redis","global::swagger"})
package gentledog.members.members.members.security;

import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.NamedInterface;

