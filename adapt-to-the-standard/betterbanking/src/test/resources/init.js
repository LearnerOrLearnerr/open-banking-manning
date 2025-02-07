print ("***************")
print ("init.js started")
print ("***************")


db = db.getSiblingDB('betterbanking');

db.createCollection("transactions")
db.getCollection("transactions").insert ({currency: "GBP", amount: 555, accountNumber: 123});
db.getCollection("transactions").insert ({currency: "GBP", amount: 131, accountNumber: 123});

print ("****************")
print ("completed init.js")
print ("****************")