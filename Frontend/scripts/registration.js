
/*
//Objekt 'person'
const person = {
    sex: 'femal',
    firstName: 'Klara',
    lastName: 'Steinauer',
    address: {
        street: 'Fleschgasse',
        plz: 1130,
        city: 'Wien'
    },
    'e-mail': 'klara.steinauer@hotmail.com',
    password: false,
    'password-repead': true
}
*/

//OOP - Person Klasse + Constructor
class Person {
    constructor(sex, fistName, lastName, street, plz, city, eMail, password, repeadPassword, dob) {
        this.sex = sex;
        this.firstName = fistName;
        this.lastName = lastName;
        this.street = street;
        this.plz = plz;
        this.city = city;
        this.eMail = eMail;
        this.password = password;
        this.repeadPassword = repeadPassword;
        this.dob = new Date(dob)
    }
}

Person.prototype.getBirthYear = function () {
    return this.dob.getFullYear();
}

//erstellen eines neuen Person Objects
//const person1 = new Person('female', 'Klara', 'Steinauer', 'Fleschgasse', 1130, 'Wien', 'klara.steinauer@hotmail.com', true, true, '05-26-1995')

//click event für den Register Button
/*const button = document.querySelector('.btn-primary');

button.addEventListener('click', (e) => {
    e.preventDefault();
    console.log(e)
})
*/

const form = document.querySelector('#registrationForm');
const titleInput = document.querySelector('#floatingAnrede');
const firstNameInput = document.querySelector('#floatingFirstname')
const lastNameInput = document.querySelector('#floatingLastname')
const streetInput = document.querySelector('#floatingStreet')
const plzInput = document.querySelector('#floatingPLZ')
const cityInput = document.querySelector('#floatingPlace')
const eMailInput = document.querySelector('#floatingInput')
const passwordInput = document.querySelector('#floatingPassword')
const passwordRepeadInput = document.querySelector('#floatingRepeatPassword')

form.addEventListener('submit', onSubmit);

function onSubmit(e) {
    e.preventDefault();

    if (lastNameInput.value === '' || eMailInput.value === '') {
        alert('Please enter fields');
        //msg.classList.add('error');
        //msg.innerHTML = 'Please enter all fields'
        //setTimeout(() => msg.remove(), 3000);
    } else {
        const user = new Person(titleInput.value, firstNameInput.value, lastNameInput.value, streetInput.value, plzInput.value, cityInput.value, eMailInput.value, passwordInput.value, passwordRepeadInput.value, '05-05-1995')
        console.log(user);
    }
}