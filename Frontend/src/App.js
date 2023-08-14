import React from 'react'
import { BrowserRouter, Routes , Route } from 'react-router-dom'
import HomePage from './component/HomePage.js'
import NavBar from './component/NavBar.js'
import Login from './component/Login.js'
import Services from './component/Services.js'
import Contact from './component/Contact.js'
import Register from './component/Register.js'


export const App = () => {
  return (
    <BrowserRouter>
    <NavBar/>
        <Routes>
            <Route path='/' element={<HomePage/>}></Route>
            <Route path='/login' element={<Login/>}></Route>
            <Route path='/services' element={<Services/>}></Route>
            <Route path='/contact' element={<Contact/>}></Route>
            <Route path='/register' element={<Register/>}></Route>
        </Routes>
    </BrowserRouter>
  )
}

export default App;