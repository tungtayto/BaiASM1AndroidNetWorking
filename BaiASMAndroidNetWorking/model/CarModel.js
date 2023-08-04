const mongoose = require('mongoose');
const { Schema } = mongoose;
const CarSchema = new mongoose.Schema({
    nameCar: {
        type: String,
        require: true
    },
    colorCar: {
        type: String,
        require: true
    },
    yearCar: {
        type: Number,
        require: true
    },
    engineTypeCar: {
        type: String,
        require: true
    },
    priceCar: {
        type: Number,
        require: true
    },
    quantityCar: {
        type: Number,
        require: true
    },
    imgCar: {
        type: String,
        require: true,
    }
})

const CarModel = new mongoose.model('cars',CarSchema);
module.exports = CarModel;