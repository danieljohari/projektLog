const loginForm = document.getElementById("login-form")
const loginFormSubtmit = document.getElementById("login-submit")
const loginFormErrMsg = document.getElementById("login-error-msg")

loginFormSubtmit.addEventListener("click",(e) =>{
    e.preventDefault();
    const CPR = loginForm.CPR.value;
    const Pass = loginForm.Pass.value;

    if(CPR == "1234" && Pass == "hejmeddig") {
        location.replace('Forside.html');
    } else {
        loginFormErrMsg.style.opacity = 1;
    }
})




