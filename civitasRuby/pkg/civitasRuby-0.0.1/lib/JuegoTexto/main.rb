#encoding:utf-8

require_relative "controlador.rb"

# require "pry"

#binding.pry

jugadores = []
jugadores.push ("* J1 *")
jugadores.push ("* J2 *")

vista = Civitas::Vista_textual.new()
juego = Civitas::CivitasJuego.new(jugadores)
Civitas::Dado.instance.setDebug(true)
controlador = Controlador.new(juego, vista)

