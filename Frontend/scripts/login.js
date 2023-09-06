/* $.ajax({
    url: 'http://localhost:8080/login',
    data: {
        email,
        password
    },
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    },
    success: (response) => {
        const accessToken = response.accessToken;
        localStorage.setItem('accessToken', accessToken);
        //später verwenden mit localSotrage.getItem('accessToken')
        window.location.href = '/admin';
    },
    error: (err) => {
        //lese error und zeige dem error entsprechend eine 
    }
}) */

