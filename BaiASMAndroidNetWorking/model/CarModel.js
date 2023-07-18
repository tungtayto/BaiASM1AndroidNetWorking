const mongoose = require('mongoose');
const { Schema } = mongoose;
const CarSchema = new mongoose.Schema({
    name: {
        type: String,
        require: true
    },
    price: {
        type: Number,
        require: true
    },
    quantity: {
        type: Number,
        require: true
    },
})

const CarModel = new mongoose.model('cars',CarSchema);
module.exports = CarModel;