# tpv-springboot
TPV con swing y springboot

I found an error running with MySQL 5.6, when I try to create a product with a product group it fail saying:


[WARNING]
org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.brasajava.model.Producto; nested exception is java.lang.IllegalStateException: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.brasajava.model.Producto

Caused by: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.brasajava.model.Producto
