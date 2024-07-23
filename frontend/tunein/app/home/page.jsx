'use client'

import React from 'react'

const HomePage = () => {

    const userDetails = JSON.parse(localStorage.getItem("userDetails"));

    return (
        <div>
            <h1>{userDetails.username}</h1>
        </div>
    )
}

export default HomePage
