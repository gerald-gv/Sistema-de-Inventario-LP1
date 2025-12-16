<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login - Sistema de Inventario</title>

    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <style>
        .input-icon {
            position: absolute;
            left: 12px;
            top: 50%;
            transform: translateY(-50%);
            color: #6b7280;
        }

        .password-toggle {
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #6b7280;
        }

        .login-card {
            animation: fadeSlide 0.6s ease-out;
        }

        @keyframes fadeSlide {
            from {
                opacity: 0;
                transform: translateY(15px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        input {
            transition: border-color 0.2s ease, box-shadow 0.2s ease;
        }

        button {
            transition: background-color 0.2s ease, transform 0.1s ease;
        }

        button:active {
            transform: scale(0.98);
        }
    </style>
</head>

<body class="bg-slate-100 flex items-center justify-center min-h-screen">

    <div class="login-card bg-white w-full max-w-sm p-8 rounded-xl shadow-lg">

        <div class="text-center mb-6">
            <div class="text-sky-600 text-4xl mb-2">
                <i class="fa-solid fa-boxes-stacked"></i>
            </div>
            <h2 class="text-2xl font-bold text-slate-700">
                Sistema de Inventario
            </h2>
            <p class="text-sm text-slate-500 mt-1">
                Iniciar sesión
            </p>
        </div>

        <% if (request.getAttribute("errorLogin") != null) { %>
            <div class="mb-4 p-3 rounded-lg bg-red-100 text-red-700 text-sm flex items-center gap-2">
                <i class="fa-solid fa-circle-exclamation"></i>
                <span><%= request.getAttribute("errorLogin") %></span>
            </div>
        <% } %>

        <form action="LoginServlet" method="POST" class="space-y-5">
            <div>
                <label class="block text-sm font-medium text-slate-600 mb-1">
                    Correo electrónico
                </label>
                <div class="relative">
                    <i class="fa-solid fa-envelope input-icon"></i>
                    <input
                        type="email"
                        name="email"
                        required
                        class="w-full pl-10 pr-3 py-2 border rounded-lg focus:outline-none focus:ring-1 focus:ring-sky-500">
                </div>
            </div>

            <div>
                <label class="block text-sm font-medium text-slate-600 mb-1">
                    Contraseña
                </label>
                <div class="relative">
                    <i class="fa-solid fa-lock input-icon"></i>
                    <input
                        type="password"
                        id="contrasena"
                        name="contrasena"
                        required
                        class="w-full pl-10 pr-10 py-2 border rounded-lg focus:outline-none focus:ring-1 focus:ring-sky-500">

                    <span id="togglePassword" class="password-toggle">
                        <i class="fa-solid fa-eye-slash"></i>
                    </span>
                </div>
            </div>

            <button
                type="submit"
                class="w-full bg-sky-600 text-white py-2 rounded-lg font-semibold hover:bg-sky-700">
                <i class="fa-solid fa-right-to-bracket mr-2"></i>
                Entrar
            </button>

        </form>
    </div>

	<div id="toastContainer" class="fixed top-5 right-5 z-50 space-y-3"></div>

    <script>
        const passwordInput = document.getElementById('contrasena');
        const toggleButton = document.getElementById('togglePassword');

        toggleButton.addEventListener('click', function () {
            const type = passwordInput.type === 'password' ? 'text' : 'password';
            passwordInput.type = type;

            const icon = this.querySelector('i');
            icon.classList.toggle('fa-eye');
            icon.classList.toggle('fa-eye-slash');
        });
    </script>
</body>
</html>