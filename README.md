# tpv-springboot
TPV con swing y springboot

I found an error when I try to create a product and set the group before save the product for the first time. The error messag say:


[WARNING]
org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.brasajava.model.Producto; nested exception is java.lang.IllegalStateException: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.brasajava.model.Producto

Caused by: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.brasajava.model.Producto
