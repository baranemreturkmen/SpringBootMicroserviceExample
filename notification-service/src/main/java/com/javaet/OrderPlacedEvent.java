package com.javaet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*FIXME: In general not recommended to share any classes between these 2 services because we want to keep these services
*  independent and clean so its always recommended espically if you're are receive and sending payloads you have to own
*  versions of your classes inside your own services. We've the same class in order service and we just copy and pasted here
*  it's not recommended. It should be in a jar file or sth. Fix this.*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private String orderNumber;
}
