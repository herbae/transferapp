# Money transfer (single application)

This is an early version of the money transfer application.

This application was built with the following stack:

- Spring Boot
- JPA
- HATEOAS
- Spring Security
- H2 Database (_in memory_)

## How to run

You can run the application with `mvn spring-boot:run`. Default URL is http://localhost:8080.

## Credentials

This application uses Basic Authentication. There is only one user with access to this application:
`user1:password1`.

## API

* *GET* `/accounts`
* *GET* `/accounts/{id}`
* *POST* `/accounts`
```
{
    balance: <balance>
}
```
* *DELETE* `/accounts/{id}`
* *POST* `/transfer`
```
{
    'fromAccountId': <account_id>,
    'toAccountId': <account_id>,
    'amount': <amount>,
    'currencyId': <currencyId>,
    'description': <description>
}
```
* *GET* `/transfer/{id}`

Sample:
```
curl --user user1:passuser1 -X POST -H "Content-Type: application/json" \
	-d '{ "fromAccountId": 2, "toAccountId": 1, "amount": 1000, "currencyId": "USD", "description": "basic transfer" }' \
	http://localhost:8080/transfers
```
Expected output:
> {"id":"7a6b46ee-6a58-423f-a23d-36dca6f71995","taxCollected":50.00,"amountInCAD":1289.48}
