//document.addEventListener("DOMContentLoaded", function() {

const listChocolateTemplateText = document.querySelector(".listChocolateTemplate");
const listChocolateTemplate = Handlebars.compile(listChocolateTemplateText.innerText);

const chocolates = document.querySelector(".chocolates");
const qty = document.querySelector(".qty");
const product = document.querySelector(".product");

const getData = () => {
    axios.get("/api/chocolates")
        .then(function (result) {
            chocolates.innerHTML = listChocolateTemplate({ chocolates: result.data })
        })
        .catch(function (err) {
            console.log(err);
        });
    console.log('done')
}

const addChocolate = async () => {
    const data = {
        name: product.value,
        qty: Number(qty.value)
    }
    if(data.product.length) {
        await axios.post("/api/chocolates", data).then(result => getData());
    }
}

const removeProduct = (name) => {
    axios.post("/api/chocolates/remove", { name })
        .then(result => getData())
        .catch(err => console.log({ err }))
}

const eatProduct = async (name) => {
    await axios.post("/api/chocolates/eat", { name })
        .then(result => getData())
        .catch(err => console.log({err}))
}
getData();
//});

