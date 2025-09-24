db = db.getSiblingDB('example_edu_db');

db.users.insertOne({
    "_id": "550e8400-e29b-41d4-a716-446655440000",
    "email": "teacher@example.com",
    "encryptedPassword": "$2a$12$s.ZqtNtAmhfzGrgsjHT8Z.2LAh.PlKOSHjkzs9Z/5NSiWg091FpJG", // Senha: password123
    "name": "Professor Exemplo",
    "cpf": "25201787010",
    "phone": "21999999999",
    "address": {
        "street": "Rua Projetada Quatro",
        "number": "33",
        "complement": null,
        "neighborhood": "Bangu",
        "city": "Rio de Janeiro",
        "state": "RJ",
        "cep": "21862650"
    },
    "role": "TEACHER"
});