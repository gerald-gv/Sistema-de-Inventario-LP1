<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login - Sistema de Inventario</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    
    <style>
        .contrasena-container {
            position: relative;
        }
        .contrasena-toggle {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #4b5563;
            padding: 5px;
        }
    </style>
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen">
    <div class="bg-white p-8 rounded-lg shadow-md w-full max-w-sm">
        <h2 class="text-2xl font-bold mb-6 text-center">Iniciar Sesión</h2>
        
        <% if (request.getAttribute("errorLogin") != null) { %>
            <div class="p-3 mb-4 text-sm text-red-700 bg-red-100 rounded-lg text-center" role="alert">
                <%= request.getAttribute("errorLogin") %>
            </div>
        <% } %>

        <form action="LoginServlet" method="POST"> 
            <div class="mb-4">
                <label for="email" class="block text-gray-700 font-bold mb-2">Correo Electrónico</label>
                <input type="email" id="email" name="email" class="w-full px-3 py-2 border rounded-lg focus:outline-none" required>
            </div>
             
            <div class="mb-6">
                <label for="password" class="block text-gray-700 font-bold mb-2">Contraseña</label>
                <div class="contrasena-container">
                    <input type="password" id="contrasena" name="contrasena" class="w-full px-3 py-2 border rounded-lg focus:outline-none" required>
                    <span id="togglePassword" class="contrasena-toggle">
                        <i class="fas fa-eye-slash"></i> 
                    </span>
                </div>
            </div>
            
            <button type="submit" class="w-full bg-green-600 text-white py-2 rounded-lg hover:bg-green-700 transition duration-200">
                Entrar
            </button>
        </form>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const passwordInput = document.getElementById('contrasena');
            const toggleButton = document.getElementById('togglePassword');

            toggleButton.addEventListener('click', function () {
                const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
                
                
                const icon = this.querySelector('i');
                if (type === 'text') {
                    icon.classList.remove('fa-eye-slash');
                    icon.classList.add('fa-eye');
                } else {
                    icon.classList.remove('fa-eye');
                    icon.classList.add('fa-eye-slash');
                }
            });
        });
    </script>
</body>
</html>