# open-banking-manning
Covers manning live project

## Pulling a local copy

```
mkdir open-banking
cd open-banking
git init
git remote add origin https://github.com/learnerOrLearnerr/open-banking-manning/
git checkout main
git pull origin main
```

## Define the Contract
The first part of the live project is defining the contract for better-banking.io, a FinTech startup.

### MongoDB dependency
JPA with MongoDB is used in the project. Please see the [gist for running MongoDB in a docker container](https://gist.github.com/LearnerOrLearnerr/fddf0a5388b0fc71c04d26152c293003) on MS Windows.

## Adapter the Standard
OpenAPI generated code (based on OpenBanking) is added along with REST client to fetch the transaction history. Circuit breaker using resilience4j is added as well.