<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/gsap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/ScrollTrigger.min.js"></script>
<script src="https://cdn.tailwindcss.com"></script>

<style>
    #logo-mask {
        background: white;
        mask-image: url("${pageContext.request.contextPath}/img/mask.svg");
        mask-position: center 25%;
        mask-repeat: no-repeat;
        mask-size: clamp(6000vh, 4500%, 6000vh);
    }
</style>

<div id="intro-wrapper" class="fixed inset-0 z-[99999] overflow-y-scroll bg-black">
    <!-- Este div FIXED se mantiene visible durante la animación -->
    <div id="logo-mask" class="fixed inset-0" style="pointer-events: none;">
        <section>
            <div id="hero-key" class="h-screen scale-125 overflow-hidden relative w-full">
                <img id="hero-keys-logo" 
                     src="${pageContext.request.contextPath}/img/Logo-fondo.svg"
                     class="absolute w-full h-full object-cover" />
                <img id="hero-keys-logobackground" 
                     src="${pageContext.request.contextPath}/img/FondoLibreria.png"
                     class="w-full h-full object-cover" />
            </div>
        </section>
    </div>
    <img id="hero-new-image"
     src="${pageContext.request.contextPath}/img/Frase.svg"
     class="fixed inset-0 w-full h-full object-cover opacity-0 scale-105 pointer-events-none z-[100000]" />
	
    <!-- DIV PARA GENERAR ESPACIO, SI NO NO FUNCIONA TODO ESTO -->
    <div id="scroll-spacer" class="relative h-[750vh]" style="pointer-events: auto;"></div>
</div>

<script>
if (sessionStorage.getItem("introSeen")) {
    document.getElementById("intro-wrapper")?.remove();
    if (typeof ScrollTrigger !== 'undefined') {
        ScrollTrigger.refresh();
    }
} else {
    sessionStorage.setItem("introSeen", "true");

    gsap.registerPlugin(ScrollTrigger);
	//HACEMOS LA LINEA DE TIEMPO PARA LA ANIMACION
    const tl = gsap.timeline({
        defaults: { ease: "power2.out" }, //ESTA ES LA "CURVA" DE MOVIMIENTO, QUE ES POWER2.OUT, QUE SIGNIFICA INICIA LENTO Y TERMINA RAPIDO
        scrollTrigger: {
            trigger: "#scroll-spacer",  //EMPIEZA LA ANIMACION AL DIV DE SCROLL-SPACER
            scroller: "#intro-wrapper",  //EL CONTENEDOR QUE TIENE EL SCROLL
            start: "top top",  //CUANDO INICIA
            end: "bottom bottom",  //CUANDO TERMINA
            scrub: 10,  //LA SUAVIDAD AL BAJAR/SUBIR EL SCROLL, SE PUEDE DECIR QUE ES EL DELAY
            snap: 0.15,  //DIVIDE EL MOVIMIENTO DEL SCROLL PARA QUE NO TENGA PAROS REPENTINOS
            invalidateOnRefresh: true,  //PARA RECALCULAR LAS REDIMENCIONES, RESPONSIVE
            markers: false,  //SOLO PARA DEBUG, SE QUEDA EL FALSE

            onLeave: () => {  //SE EJECUTA AL TERMINAR LA ANIMACION ""
                const wrapper = document.getElementById("intro-wrapper");
                if (!wrapper) return;

                // Mata todos los triggers antes de animar salida
                ScrollTrigger.getAll().forEach(t => t.kill());

                // ANIMACION DE SALIDA (1s)
                gsap.to(wrapper, {
                    opacity: 0,
                    scale: 0.98,
                    duration: 1,
                    ease: "power2.out",
                    onComplete: () => {  //LIMPIEZA FINAL, REFRESCA EL SCROLLTRIGGER.
                        wrapper.remove();
                        ScrollTrigger.refresh();
                        document.body.style.overflow = 'auto';
                    }
                });
            }
        }
    });

    tl.to("#hero-key", {
        duration: 1,
        scale: 1
    })
    .to("#hero-keys-logo", {
        opacity: 0
    }, "<")
.to("#logo-mask", {
    webkitMaskSize: "clamp(20vh, 25%, 30vh)",
    maskSize: "clamp(20vh, 25%, 30vh)",
}, 0.2) 
    .to("#hero-key", {
        opacity: 0,
    }, 0.5).to("#hero-new-image", {
        opacity: 1,
        scale: 1,
        duration: 1.2,
        ease: "power3.out"
    },"-=0.1")
    .to("#intro-wrapper", {
        pointerEvents: "none",
        duration: 0.01
    })
    .to("#logo-mask", {
        opacity: 0,
        duration: 0.5
    });
}
</script>
